import { useEffect, useState } from "react";

import Modal from "../../components/Modal/Modal";
import ConductorForm from "./ConductorForm";
import ConductorTable from "./ConductorTable";


import {
    listarConductores,
    crearConductor,
    actualizarConductor,
    eliminarConductor
} from "../../services/conductoresService";

function ConductoresPage() {

    const [conductores, setConductores] = useState([]);
    const [open, setOpen] = useState(false);
    const [edit, setEdit] = useState(null);

    useEffect(() => {
        cargar();
    }, []);

    const cargar = async () => {
        try {
            const res = await listarConductores();
            console.log(res.data);

            // ApiResponse -> data -> Array
            setConductores(res.data);

        } catch (error) {
            console.error(error);
        }
    };

    const handleSubmit = async (data) => {
        
        try {
            if (edit) {
                await actualizarConductor(edit.id, data);
            } else {
                await crearConductor(data);
            }
            setOpen(false);
            setEdit(null);

            cargar();
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async (id) => {

        if (!window.confirm("¿Desea eliminar este conductor?")) {
            return;
        }

        try {

            await eliminarConductor(id);

            cargar();

        } catch (error) {
            console.error(error);
        }

    };

    return (

        <div className="conductores-page">

            <div className="page-header">

                <h1>Conductores</h1>

                <button
                    onClick={() => {

                        setEdit(null);

                        setOpen(true);

                    }}
                >
                    Nuevo Conductor
                </button>

            </div>

            <ConductorTable
                conductores={conductores}
                onEdit={(c) => {

                    setEdit(c);

                    setOpen(true);

                }}
                onDelete={handleDelete}
            />

            <Modal
                isOpen={open}
                onClose={() => {

                    setOpen(false);

                    setEdit(null);

                }}
                title={edit ? "Editar Conductor" : "Nuevo Conductor"}
            >

                <ConductorForm
                    conductor={edit}
                    onSubmit={handleSubmit}
                />

            </Modal>

        </div>

    );

}

export default ConductoresPage;