import { useEffect, useState } from "react";
import RoleGuard from "../../components/Auth/RoleGuard";
import Modal from "../../components/Modal/Modal";
import UsuarioForm from "./UsuarioForm";
import UsuarioTable from "./UsuarioTable";

import {
    listarUsuarios,
    crearUsuario,
    actualizarUsuario,
    eliminarUsuario
} from "../../services/usuariosService";

function UsuariosPage() {

    const [usuarios, setUsuarios] = useState([]);
    const [open, setOpen] = useState(false);
    const [edit, setEdit] = useState(null);

    useEffect(() => {
        cargar();
    }, []);

    const cargar = async () => {

        const res = await listarUsuarios();

        setUsuarios(res.data);

    };

    const handleSubmit = async (data) => {

        if (edit) {
            await actualizarUsuario(edit.id, data);
        } else {
            await crearUsuario(data);
        }

        setOpen(false);
        setEdit(null);

        cargar();

    };

    const handleDelete = async (id) => {

        const confirmDelete = window.confirm("¿Eliminar usuario?");

        if (!confirmDelete) return;

        await eliminarUsuario(id);

        cargar();

    };

    return (

        <div className="usuarios-page">

            <div className="page-header">

                <h1>Usuarios</h1>
        
                <RoleGuard roles={["ADMIN"]}>
                    <button
                        className="btn-primary"
                        onClick={() => {
                            setEdit(null);
                            setOpen(true);
                        }}
                    >
                        Nuevo Usuario
                    </button>
                </RoleGuard>
            </div>

            <UsuarioTable
                usuarios={usuarios}
                onEdit={(usuario) => {
                    setEdit(usuario);
                    setOpen(true);
                }}
                onDelete={handleDelete}
            />

            <Modal
                isOpen={open}
                title={edit ? "Editar Usuario" : "Nuevo Usuario"}
                onClose={() => setOpen(false)}
            >
                <UsuarioForm
                    usuario={edit}
                    onSubmit={handleSubmit}
                />
            </Modal>
        </div>
    );
}

export default UsuariosPage;