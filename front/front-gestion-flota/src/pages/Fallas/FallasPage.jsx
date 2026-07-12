import { useEffect, useState } from "react";

import RoleGuard from "../../components/Auth/RoleGuard";
import Modal from "../../components/Modal/Modal";
import FallaForm from "./FallaForm";
import FallaTable from "./FallaTable";

import {
    listarFallas,
    crearFalla,
    actualizarFalla,
    eliminarFalla
} from "../../services/fallaService";

function FallasPage() {

    const [fallas, setFallas] = useState([]);
    const [open, setOpen] = useState(false);
    const [edit, setEdit] = useState(null);

    useEffect(() => {
        cargar();
    }, []);

    const cargar = async () => {
        const response = await listarFallas();
        setFallas(response.data);
    };

    const handleSubmit = async (data) => {

        if (edit) {
            await actualizarFalla(edit.id, data);
        } else {
            await crearFalla(data);
        }

        setOpen(false);
        setEdit(null);
        cargar();

    };

    return (

        <div>

            <div className="page-header">

                <h1>Fallas</h1>

                    <button
                        className="btn-primary"
                        onClick={() => {
                            setEdit(null);
                            setOpen(true);
                        }}
                    >
                        Nueva Falla
                    </button>

            </div>

            <FallaTable
                fallas={fallas}
                onEdit={(f) => {
                    setEdit(f);
                    setOpen(true);
                }}
                onDelete={async (id) => {
                    await eliminarFalla(id);
                    cargar();
                }}
            />

            <RoleGuard roles={["ADMIN", "OPERADOR"]}>

                <Modal
                    isOpen={open}
                    title={edit ? "Editar Falla" : "Nueva Falla"}
                    onClose={() => setOpen(false)}
                >
                    <FallaForm
                        falla={edit}
                        onSubmit={handleSubmit}
                    />
                </Modal>

            </RoleGuard>

        </div>

    );
}

export default FallasPage;