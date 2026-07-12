import { useEffect, useState } from "react";
import RoleGuard from "../../components/Auth/RoleGuard";

import Modal from "../../components/Modal/Modal";
import MantencionForm from "./MantencionForm";
import MantencionTable from "./MantencionTable";

import RoleGuard from "../../components/Auth/RoleGuard";

import {
    listarMantenciones,
    crearMantencion,
    actualizarMantencion,
    eliminarMantencion
} from "../../services/mantencionService";

function MantencionesPage() {

    const [mantenciones, setMantenciones] = useState([]);
    const [open, setOpen] = useState(false);
    const [edit, setEdit] = useState(null);

    useEffect(() => {
        cargar();
    }, []);

    const cargar = async () => {

        const res = await listarMantenciones();

        setMantenciones(res.data);

    };

    const handleSubmit = async (data) => {

        if (edit) {
            await actualizarMantencion(edit.id, data);
        } else {
            await crearMantencion(data);
        }

        setOpen(false);
        setEdit(null);

        cargar();

    };

    const handleDelete = async (id) => {

        if (!window.confirm("¿Eliminar mantención?")) return;

        await eliminarMantencion(id);

        cargar();

    };

    return (

        <div className="mantenciones-page">

            <div className="page-header">

                <h1>Mantenciones</h1>

                <button
                    className="btn-primary"
                    onClick={() => {
                        setEdit(null);
                        setOpen(true);
                    }}
                >
                    Nueva Mantención
                </button>

            </div>

            <MantencionTable
                mantenciones={mantenciones}
                onEdit={(m) => {
                    setEdit(m);
                    setOpen(true);
                }}
                onDelete={handleDelete}
            />

            <Modal
                isOpen={open}
                title={edit ? "Editar Mantención" : "Nueva Mantención"}
                onClose={() => setOpen(false)}
            >
                <MantencionForm
                    mantencion={edit}
                    onSubmit={handleSubmit}
                />
            </Modal>

        </div>

    );
}

export default MantencionesPage;